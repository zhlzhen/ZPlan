package concurrent.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
    private static final Executor exec1 = new ThreadPerTaskExecutor();
    private static final Executor exec2 = new WithinThreadExecutor();

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
//                    handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }
}

class ThreadPerTaskExecutor implements Executor {
    public void execute(Runnable r) {
        new Thread(r).start();
    };
}

class WithinThreadExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    };
}




