import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncFileReader {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void readAsync(String filePath) {
        Path path = Paths.get(filePath);

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            channel.read(buffer, 0, buffer, executor, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if (result == -1) {
                        System.out.println("File reading completed.");
                        return; // Crucial: Exit the recursion
                    }

                    attachment.flip();
                    byte[] bytes = new byte[attachment.remaining()];
                    attachment.get(bytes);
                    System.out.print(new String(bytes));
                    attachment.compact();  // Crucial: Prepare buffer for the next read

                    // Resubmit the task.  Critically, don't call channel.read again if result == -1
                    channel.read(attachment, 0, attachment, executor, this);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.err.println("File reading failed: " + exc.getMessage());
                }
            });

        } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
        } finally {
            // Very Important:  Shutdown the executor
            if (!executor.isShutdown()) {
                executor.shutdown();
            }
        }
    }


    public static void main(String[] args) {
        String filePath = "my_file.txt";

        readAsync(filePath);
        System.out.println("\nMain thread continues after async read...");
    }
}