package life.lixiaoyu.helloandroid.async;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import life.lixiaoyu.helloandroid.R;

public class AsyncDemoActivity extends AppCompatActivity {

    private static final int MSG_WHAT_FLAG_1 = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_generated);
//        Handler handler = new Handler();
//        new Thread() {
//            @Override
//            public void run() {
//                // 模拟后台耗时任务
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//
//                }
//                // 存数据库等操作
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(AsyncDemoActivity.this, "任务执行完成", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }.start();
//        Looper.myLooper();
//
//        HandlerThread handlerThread = new HandlerThread("second");
//        Handler handler2 = new Handler(handlerThread.getLooper());
//        handler2.sendEmptyMessage(10);

        MyAsyncTask task = new MyAsyncTask();
        task.execute("My Task");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed");
            }
        });

        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Task executed");
            }
        });

        HandlerThread thread = new HandlerThread("HandlerThread");
        thread.start();
        thread.setPriority(3);
        android.os.Process.setThreadPriority(-20);
        ThreadHandler handler = new ThreadHandler(thread.getLooper());
        handler.sendEmptyMessage(MSG_WHAT_FLAG_1);
        thread.quitSafely();

        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);

        LooperThread looperThread = new LooperThread("looper-thread");
        looperThread.start();

        Handler myHandler = new Handler(looperThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.d("OWEN", "handleMessage: " + msg.what);
                Log.d("OWEN", "handleMessage: " + Thread.currentThread().getName());
            }
        };
        myHandler.sendEmptyMessage(MSG_WHAT_FLAG_1);
    }

    static class ThreadHandler extends Handler {
        public ThreadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_WHAT_FLAG_1:
                    System.out.println("Receive MSG_WHAT_FLAG_1");
                    break;
            }
        }
    }

    static class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private static final String TAG = "MyAsyncTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, Thread.currentThread().getName() + " onPostExecute");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, Thread.currentThread().getName() + " doInBackground");
            for (int i = 0; i < 10; i++) {
                publishProgress(i);
            }
            return "Success";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, Thread.currentThread().getName() + " onPostExecute, Result: " + result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, Thread.currentThread().getName() + " onProgressUpdate, progress: " + values[0]);
        }
    }
}
