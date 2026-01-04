package com.Nextera.ProNetService.Controller;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setUserId(1);
        User user2 = new User();
        user2.setUserId(2);

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetCurrentUserInfo() {
        Principal principal = () -> "alice@example.com";
        User user = new User();
        user.setUserId(1);

        when(userService.getCurrentUserInfo("alice@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<Optional<User>> response = userController.getCurrentUserInfo(principal);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isPresent());
        assertEquals(1, response.getBody().get().getUserId());
        verify(userService, times(1)).getCurrentUserInfo("alice@example.com");
    }

    @Test
    void testUploadProfilePicture() throws Exception {
        MultipartFile file = new MockMultipartFile(
                "file", "profile.png", "image/png", "test-data".getBytes()
        );

        doNothing().when(userService).uploadProfilePicture(1, file);

        ResponseEntity<String> response = userController.uploadProfilePicture(1, file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Profile picture uploaded successfully.", response.getBody());
        verify(userService, times(1)).uploadProfilePicture(1, file);
    }

    @Test
    void testDeleteProfilePicture() throws Exception {
        doNothing().when(userService).deleteProfilePicture(1);

        ResponseEntity<String> response = userController.deleteProfilePicture(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Profile picture deleted successfully.", response.getBody());
        verify(userService, times(1)).deleteProfilePicture(1);
    }

    @Test
    void testGetProfilePicture() {
        byte[] imageBytes = "test-image".getBytes();
        User user = new User();
        user.setUserId(1);
        user.setProfilePicture(imageBytes);
        user.setProfilePictureContentType("image/png");

        when(userService.getProfilePicture(1)).thenReturn(Optional.of(user));

        ResponseEntity<byte[]> response = userController.getProfilePicture(1);

        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(imageBytes, response.getBody());
        assertEquals("image/png", response.getHeaders().getFirst("Content-Type"));
        verify(userService, times(1)).getProfilePicture(1);
    }

    @Test
    void testGetProfilePictureUserNotFound() {
        when(userService.getProfilePicture(1)).thenReturn(Optional.empty());

        ResponseEntity<byte[]> response = userController.getProfilePicture(1);

        assertEquals(404, response.getStatusCodeValue());
        verify(userService, times(1)).getProfilePicture(1);
    }



    @Test
    void testGetProfilePictureNoImage() {
        User user = new User();
        user.setUserId(1);
        user.setProfilePicture(null);

        when(userService.getProfilePicture(1)).thenReturn(Optional.of(user));

        ResponseEntity<byte[]> response = userController.getProfilePicture(1);

        // Expect OK, not 404
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Ensure body is returned
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);

        // Ensure content type is correct
        assertEquals("image/png", response.getHeaders().getFirst("Content-Type"));

        verify(userService, times(1)).getProfilePicture(1);
    }



}
