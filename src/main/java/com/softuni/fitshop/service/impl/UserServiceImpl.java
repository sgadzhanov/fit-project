package com.softuni.fitshop.service.impl;

import com.softuni.fitshop.model.entity.UserEntity;
import com.softuni.fitshop.model.entity.UserRoleEntity;
import com.softuni.fitshop.model.entity.enums.UserExperienceEnum;
import com.softuni.fitshop.model.entity.enums.UserRoleEnum;
import com.softuni.fitshop.model.service.UserPictureServiceModel;
import com.softuni.fitshop.model.service.UserRegisterServiceModel;
import com.softuni.fitshop.model.view.UserViewModel;
import com.softuni.fitshop.repository.UserRepository;
import com.softuni.fitshop.repository.UserRoleRepository;
import com.softuni.fitshop.service.UserService;
import com.softuni.fitshop.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FitShopUserDetailsService fitShopUserDetailsService;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder, FitShopUserDetailsService fitShopUserDetailsService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;

        this.passwordEncoder = passwordEncoder;
        this.fitShopUserDetailsService = fitShopUserDetailsService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initUsers() {
        initUsersRoles();

        if (this.userRepository.count() != 0) {
            return;
        }

        var userEntity = new UserEntity();

        var adminRole = this.userRoleRepository.findByRoleEnum(UserRoleEnum.ADMIN);
        var userRole = this.userRoleRepository.findByRoleEnum(UserRoleEnum.USER);

        userEntity
                .setUsername("stoyan")
                .setPassword(this.passwordEncoder.encode("my-pass"))
                .setEmail("myEmail@yahoo.com")
                .setExperienceLevel(UserExperienceEnum.ADVANCED)
//                .setPictureUrl("https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/00/ba/7f/00ba7f99-b0b7-39ac-0d9f-42e5cfdfe30e/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/1200x630wa.png")
                .setRoles(Set.of(adminRole, userRole));

        this.userRepository.save(userEntity);
    }

    private void initUsersRoles() {
        if (this.userRoleRepository.count() != 0) {
            return;
        }
        var admin = new UserRoleEntity();
        admin.setRoleEnum(UserRoleEnum.ADMIN);

        var user = new UserRoleEntity();
        user.setRoleEnum(UserRoleEnum.USER);

        this.userRoleRepository.saveAll(Arrays.asList(admin, user));
    }

    @Override
    public void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {
        UserRoleEntity userRole = this.userRoleRepository.findByRoleEnum(UserRoleEnum.USER);
        UserEntity userEntity = new UserEntity();
        userEntity
                .setExperienceLevel(userRegisterServiceModel.getExperienceLevel())
                .setRoles(Set.of(userRole))
                .setUsername(userRegisterServiceModel.getUsername())
                .setPassword(this.passwordEncoder.encode(userRegisterServiceModel.getPassword()))
                .setEmail(userRegisterServiceModel.getEmail());
        if (userEntity.getExperienceLevel().toString().equals("INTERMEDIATE")) {
            userEntity.setPictureUrl("https://img.freepik.com/free-photo/athletic-physical-man-training-fitness-gym-strong-handsome-male-working-with-sport-equipment_116317-10086.jpg");
        } else if (userEntity.getExperienceLevel().toString().equals("ADVANCED")) {
            userEntity.setPictureUrl("https://img2.thejournal.ie/article/2558882/river?version=2558937&width=1340");
        } else if (userEntity.getExperienceLevel().toString().equals("BEGINNER")) {
            userEntity.setPictureUrl("https://www.mychannelfitness.com/uploads/1/3/2/1/132115505/fitness-beginner-crawling-on-gym-floor_orig.jpg");
        }

        userEntity = this.userRepository.save(userEntity);

        UserDetails principal = this.fitShopUserDetailsService.loadUserByUsername(userEntity.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                userEntity.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean isUsernameFree(String username) {
        return this.userRepository
                .findByUsername(username)
                .isEmpty();

    }

    @Override
    public UserEntity getByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User " + username + " does not exist."));
    }

    @Override
    public UserViewModel getViewModelByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .orElseThrow(() -> new ObjectNotFoundException("Username " + username + " does not exist."));

    }

    @Override
    public void updateWithPicture(UserPictureServiceModel userPictureServiceModel) {
        UserEntity userEntity = this.userRepository
                .findByUsername(userPictureServiceModel.getUsername())
                .orElseThrow(() ->
                        new ObjectNotFoundException
                        ("User " + userPictureServiceModel.getUsername() + " does not exist."));

        String pictureUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Funsplash.com%2Fs%2Fphotos%2Fpersonal-trainer&psig=AOvVaw32NSzXhhLmfMhRdDisj6tn&ust=1648908672860000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCLDumNCF8_YCFQAAAAAdAAAAABAD";
        userEntity.setPictureUrl(pictureUrl);
        this.userRepository.save(userEntity);
    }

}