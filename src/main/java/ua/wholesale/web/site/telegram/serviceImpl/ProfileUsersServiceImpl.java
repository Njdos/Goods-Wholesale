package ua.wholesale.web.site.telegram.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.wholesale.web.site.telegram.model.ProfileUsers;
import ua.wholesale.web.site.telegram.model.UserTelegram;
import ua.wholesale.web.site.telegram.repos.ProfileUsersRepo;
import ua.wholesale.web.site.telegram.service.ProfileUsersService;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ProfileUsersServiceImpl implements ProfileUsersService {

    private ProfileUsersRepo profileUsersRepo;

    @Override
    public ArrayList<ProfileUsers> getByUserid(Long userId) {

        ArrayList<ProfileUsers> arrayList = profileUsersRepo.findByProfileUserid(userId);

        return  arrayList;
    }

    @Override
    public void save(UserTelegram userTelegram, Long userId) {

        userTelegram.setUserid(userId);

        ProfileUsers profileUsers = new ProfileUsers();

        ArrayList<UserTelegram> usersArrayList = new ArrayList<>();
        usersArrayList.add(userTelegram);

        profileUsers.setUserProfile(usersArrayList);
        profileUsers.setProfileUserid(userId);

        profileUsersRepo.save(profileUsers);
    }
}