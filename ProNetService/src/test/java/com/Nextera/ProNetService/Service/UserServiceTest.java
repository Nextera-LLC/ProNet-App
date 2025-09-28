package com.Nextera.ProNetService.Service;

import com.Nextera.ProNetService.Model.User;
import com.Nextera.ProNetService.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setUserId(1);
        user1.setFirstName("Alice");

        User user2 = new User();
        user2.setUserId(2);
        user2.setFirstName("Bob");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getFirstName());
        verify(userRepository, times(1)).findAll();
    }
    
    @Test
    void uploadProfilePicture() throws Exception {
        User user = new User();
        user.setUserId(1);

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "image.png",
                "image/png",
                "fake-image-content".getBytes()
        );

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.uploadProfilePicture(1, file);

        assertNotNull(user.getProfilePicture());
        assertEquals("image/png", user.getProfilePictureContentType());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteProfilePicture() {
        User user = new User();
        user.setUserId(1);
        user.setProfilePicture("some-bytes".getBytes());
        user.setProfilePictureContentType("image/png");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.deleteProfilePicture(1);

        assertNull(user.getProfilePicture());
        assertNull(user.getProfilePictureContentType());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getProfilePicture() {
        // Arrange
        User user = new User();
        byte[] imageBytes = "some-bytes".getBytes();
        user.setProfilePicture(imageBytes);
        user.setProfilePictureContentType("image/png");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getProfilePicture(1);

        // Assert
        assertTrue(result.isPresent());
        assertArrayEquals(imageBytes, result.get().getProfilePicture());
        assertEquals("image/png", result.get().getProfilePictureContentType());
        verify(userRepository, times(1)).findById(1);
    }
}
