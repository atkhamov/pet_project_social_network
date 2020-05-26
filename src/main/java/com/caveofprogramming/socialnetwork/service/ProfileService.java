package com.caveofprogramming.socialnetwork.service;


import com.caveofprogramming.socialnetwork.model.Profile;
import com.caveofprogramming.socialnetwork.model.ProfileDao;
import com.caveofprogramming.socialnetwork.model.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    ProfileDao profileDao;

    @PreAuthorize("isAuthenticated()")
    public void save(Profile profile){
        profileDao.save(profile);
    }

    @PreAuthorize("isAuthenticated()")
    public Profile getUserProfile(SiteUser user){
        return profileDao.findByUser(user);
    }
}
