package com.sixteen_night.demo.task;

import android.os.AsyncTask;

/**
 * Created by Lee on 2019/10/30 16:04.
 * Params :参数-指定不定型以用来泛用
 * Progress :任务进度
 * Result :任务结果对象 同不定型T泛用
 */
public class MyTask<T> extends AsyncTask<T, Integer, T> {
    //task执行监听接口
    private TaskListener mTaskListener;

    public void setTaskListener(TaskListener taskListener) {
        mTaskListener = taskListener;
    }

    //执行预处理准备时方法，执行于UI线程
    @Override
    protected void onPreExecute() {
        if (mTaskListener != null) {
            mTaskListener.start();
        }
    }

    //运行于UI线程，对后台任务结果做出处理，结果就是doInBackground的返回值
    @Override
    protected void onPostExecute(T t) {
        if (mTaskListener != null) {
            mTaskListener.result(t);
        }
    }

    //运行于UI线程，任务进度更新执行方法（执行子线程进度的更新）
    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mTaskListener != null) {
            mTaskListener.update(values[0]);
        }
    }

    //取消一个正在执行的任务 运行于UI线程
    @Override
    protected void onCancelled() {
        if (!isCancelled()) {
            cancel(true);
        }
    }

    //后台运行
    @Override
    protected T doInBackground(T... ts) {
        if (mTaskListener != null) {
            return (T) mTaskListener.doInBackground(ts[0]);
        }
        return null;

    }

    public interface TaskListener<T> {
        void start();

        void update(int progress);

        T doInBackground(T t);

        void result(T t);
    }
}
