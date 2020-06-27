package com.github.bael.alfa.task5.domain;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupRepository {
    private Map<String, Group> groupMap = new HashMap<>();

    public void addGroup(Group group) {
        groupMap.put(group.getCode(), group);
    }

    public Optional<Group> find(String id) {
        return Optional.ofNullable(groupMap.get(id));
    }
}
