package com.caveofprogramming.socialnetwork.service;

import com.caveofprogramming.socialnetwork.model.Interest;
import com.caveofprogramming.socialnetwork.model.InterestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterestService {

    @Autowired
    private InterestDao interestDao;

    public long count(){
        return interestDao.count();
    }

    public Interest get(String interestName){
        return interestDao.findOneByName(interestName);
    }

    public void save(Interest interest){
        interestDao.save(interest);
    }

    //@Transactional //This annotation helps to avoid storing the two pieces of data into one column at the same time
    public Interest createIfNotExists(String interestText){
        Interest interest = interestDao.findOneByName(interestText);

        if(interest == null){
            interest = new Interest((interestText));
            interestDao.save(interest);
        }
        return interest;
    }
}
