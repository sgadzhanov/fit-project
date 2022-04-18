package com.softuni.fitshop.service;

import com.softuni.fitshop.model.entity.UserEntity;
import com.softuni.fitshop.model.service.UserPictureServiceModel;
import com.softuni.fitshop.model.service.UserRegisterServiceModel;
import com.softuni.fitshop.model.view.UserViewModel;

public interface UserService {
    void initUsers();

    void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel);

    boolean isUsernameFree(String username);

    UserViewModel getViewModelByUsername(String name);

    void updateWithPicture(UserPictureServiceModel userPictureServiceModel);

    UserEntity getByUsername(String username);
}
