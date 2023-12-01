package com.example.talk.controller;

import com.example.talk.TalkApplication;
import com.example.talk.common.BaseResponse;
import com.example.talk.exception.BusinessException;
import com.example.talk.model.domain.User;
import com.example.talk.model.dto.user.UserLoginRequest;
import com.example.talk.model.dto.user.UserRegisterRequest;
import com.example.talk.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TalkApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void userRegister_ValidInput_ReturnsSuccessResponse() {
        // Arrange
        UserRegisterRequest request = new UserRegisterRequest();
        request.setAccount("test");
        request.setPassword("password");
        request.setCheckPassword("password");

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.userRegister(anyString(), anyString(), anyString())).thenReturn(1L);

        userController = new UserController(userServiceMock);

        // Act
        BaseResponse<Long> result = userController.userRegister(request);

        // Assert
        assertEquals(200, result.getCode());
        assertEquals(1L, result.getData());
        verify(userServiceMock, times(1)).userRegister("test", "password", "password");
    }

    @Test
    void userRegister_NullInput_ThrowsBadRequestException() {
        assertThrows(BusinessException.class, () -> userController.userRegister(null));
    }

    @Test
    public void userLogin_ValidInput_ReturnsSuccessResponse() {
        // Arrange
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setAccount("test");
        userLoginRequest.setPassword("password");

        User user = new User();
        user.setId(1L);
        user.setUsername("Test User");

        MockHttpServletRequest request = new MockHttpServletRequest();
        when(userService.userLogin(userLoginRequest.getAccount(), userLoginRequest.getPassword(), request)).thenReturn(user);

        // Act
        BaseResponse<User> response = userController.userLogin(userLoginRequest, request);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals(user, response.getData());
    }

    @Test
    void userLogin_NullInput_ThrowsBadRequestException() {
        assertThrows(BusinessException.class, () -> userController.userLogin(null, new MockHttpServletRequest()));
    }

    @Test
    public void testUserLogout() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        // 设置mock对象的返回值或行为
        when(userService.userLogout(request)).thenReturn(true);

        BaseResponse<Boolean> result = userController.userLogout(request);

        // 验证结果
        assertEquals(200, result.getCode());
        assertEquals(true, result.getData());

        // 验证调用 userService.userLogout(request)
        verify(userService, times(1)).userLogout(request);
    }

    @Test
    public void testUserLogout_withNullRequest() {
        assertThrows(BusinessException.class, () -> userController.userLogout(null));
    }
}
