package ch.samt.gardenwherehouse.service;

import ch.samt.gardenwherehouse.data.ItemRepository;
import ch.samt.gardenwherehouse.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findByCode(String code) {
        return itemRepository.findByCode(code);
    }

    public Item sellByCode(String code) {
        Item selledItem = itemRepository.findByCode(code);

        if (selledItem == null || selledItem.getItemCount() == 0) {
            return null;
        }

        selledItem.setItemCount(selledItem.getItemCount() - 1);


        return itemRepository.save(selledItem);
    }

    public Item addByCodeAndNumber(String code, int number) {
        Item Item = itemRepository.findByCode(code);

        if (Item == null) {
            return null;
        }

        Item.setItemCount(Item.getItemCount() + number);

        return itemRepository.save(Item);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
