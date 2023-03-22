import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RcCarControllerTest {

  @Test
  public void testValidCommands() throws IOException {
    // Arrange
    Socket socketMock = mock(Socket.class);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    when(socketMock.getOutputStream()).thenReturn(outputStream);

    RcCarController rcCarController = new RcCarController(socketMock);

    // Act
    rcCarController.forward();
    rcCarController.back();
    rcCarController.brake();

    // Assert
    String sentCommands = outputStream.toString();
    assertTrue(sentCommands.contains("forward\n"), "The 'forward' command should be sent");
    assertTrue(sentCommands.contains("back\n"), "The 'back' command should be sent");
    assertTrue(sentCommands.contains("brake\n"), "The 'brake' command should be sent");
  }
}
