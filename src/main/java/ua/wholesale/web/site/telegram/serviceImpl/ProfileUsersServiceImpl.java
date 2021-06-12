package ua.wholesale.web.site.telegram.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.telegram.model.ProfileUsers;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.repos.ProfileUsersRepo;
import ua.wholesale.web.site.telegram.service.ProfileUsersService;

import java.util.ArrayList;

@Service
public class ProfileUsersServiceImpl implements ProfileUsersService {

    @Autowired
    private ProfileUsersRepo profileUsersRepo;

    @Override
    public ArrayList<ProfileUsers> getByUserid(Long userId) {

        ArrayList<ProfileUsers> arrayList = null;

        ProfileUsers lists = profileUsersRepo.findByProfileUserId(userId);

        arrayList.add(lists);

        return  arrayList;
    }

    @Override
    public void save(UserTelegram userTelegram, Long userId) {

        ProfileUsers profileUsers = new ProfileUsers();

        ArrayList<UserTelegram> usersArrayList = new ArrayList<>();
        userTelegram.setUserid(userId);
        usersArrayList.add(userTelegram);


        profileUsers.setUserProfile(usersArrayList);
        profileUsers.setProfileUserId(userId);

        profileUsersRepo.save(profileUsers);
    }
}