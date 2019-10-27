import ru.javalab.app.MyThread;

public class Downloader {
    private String[] args;

    public void download(String... args) {
        String[] UrlsArr = args;
        MyThread[] threads = new MyThread[UrlsArr.length];
        for (int i = 0; i <UrlsArr.length; i++) {
            threads[i] = new MyThread(UrlsArr[i]);
            threads[i].start();
        }
    }
}
