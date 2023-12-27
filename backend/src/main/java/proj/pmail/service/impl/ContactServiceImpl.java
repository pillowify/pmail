package proj.pmail.service.impl;

import proj.pmail.dao.ContactDAO;
import proj.pmail.entity.Contact;
import proj.pmail.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;


    @Override
    public List<Contact> getListByCreator(String creator) {
        return contactDAO.selectListByCreator(creator);
    }

    @Override
    public boolean addContact(Contact contact) {
        return contactDAO.insert(contact) > 0;
    }

    @Override
    public Contact getContactByAddressAndCreator(String address, String creator) {
        return contactDAO.selectByAddressAndCreator(address, creator);
    }

    @Override
    public boolean deleteContact(Long[] ids) {
        return contactDAO.deleteBatchIds(Arrays.asList(ids)) > 0;
    }
}
