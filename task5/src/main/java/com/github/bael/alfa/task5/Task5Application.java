
package com.github.bael.alfa.task5;

import com.github.bael.alfa.task5.csv.CsvReader;
import com.github.bael.alfa.task5.domain.Group;
import com.github.bael.alfa.task5.domain.GroupRepository;
import com.github.bael.alfa.task5.domain.Item;
import com.github.bael.alfa.task5.domain.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
public class Task5Application {

    private ItemRepository itemRepository;
    private GroupRepository groupRepository;


    public static void main(String[] args) {
        SpringApplication.run(Task5Application.class, args);
    }

    @PostConstruct
    public void loadData() {
        loadGroups();
        loadItems();
    }

    private void loadItems() {
        CsvReader<Item> groupReader = new CsvReader<>(Item.constructor());
        groupReader.readCollection("items.csv",
                itemRepository::addItem);
    }

    private void loadGroups() {
        CsvReader<Group> groupReader = new CsvReader<Group>(Group.constructor());
        List<Group> groups =
                groupReader.readCollection("groups.csv",
                        groupRepository::addGroup);
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
