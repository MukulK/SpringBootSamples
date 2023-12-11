import com.jcraft.jsch.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class SFTPExampleTest {

    @Test
    public void testSftpUpload() throws JSchException, SftpException {
        // Mock JSch, Session, and ChannelSftp
        JSch jschMock = mock(JSch.class);
        Session sessionMock = mock(Session.class);
        ChannelSftp channelSftpMock = mock(ChannelSftp.class);

        // Mock JSch behavior
        when(jschMock.getSession(anyString(), anyString(), anyInt())).thenReturn(sessionMock);
        when(sessionMock.openChannel("sftp")).thenReturn(channelSftpMock);

        // Mock SFTP upload behavior
        doNothing().when(channelSftpMock).connect();
        doNothing().when(channelSftpMock).put(anyString(), anyString());

        // Run the SFTP code
        SFTPExample sftpExample = new SFTPExample(jschMock);
        sftpExample.uploadFile("local/file.txt", "remote/file.txt");

        // Verify that the methods were called as expected
        verify(jschMock).getSession(anyString(), anyString(), anyInt());
        verify(sessionMock).setConfig("StrictHostKeyChecking", "no");
        verify(sessionMock).setPassword(anyString());
        verify(sessionMock).connect();
        verify(sessionMock).openChannel("sftp");
        verify(channelSftpMock).connect();
        verify(channelSftpMock).put(anyString(), anyString());
        verify(channelSftpMock).disconnect();
        verify(sessionMock).disconnect();
    }
}
