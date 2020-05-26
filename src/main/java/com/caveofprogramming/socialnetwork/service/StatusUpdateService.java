package com.caveofprogramming.socialnetwork.service;

import com.caveofprogramming.socialnetwork.model.StatusUpdate;
import com.caveofprogramming.socialnetwork.model.StatusUpdateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusUpdateService {

    private final static int PAGESIZE = 10;

    @Autowired
    private StatusUpdateDao statusUpdateDao;

    public void save(StatusUpdate statusUpdate){
        statusUpdateDao.save(statusUpdate);
    }

    public StatusUpdate getLatest(){
        return statusUpdateDao.findFirstByOrderByAddedDesc();
    }

    public Page<StatusUpdate> getPage(int pageNumber){
        PageRequest pageRequest = PageRequest.of(pageNumber-1, PAGESIZE, Sort.Direction.DESC, "added");
        return statusUpdateDao.findAll(pageRequest);
    }

    public void delete(Long id) {
        statusUpdateDao.deleteById(id);
    }

    public Optional<StatusUpdate> get(Long id) {
        return statusUpdateDao.findById(id);
    }
}
