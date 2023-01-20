package com.iykescode.demo.service;

import com.iykescode.demo.config.IykescodeSchoolProps;
import com.iykescode.demo.constants.IykescodeSchoolConstants;
import com.iykescode.demo.model.Contact;
import com.iykescode.demo.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final IykescodeSchoolProps iykescodeSchoolProps;

    @Autowired
    public ContactService(ContactRepository contactRepository, IykescodeSchoolProps iykescodeSchoolProps) {
        this.contactRepository = contactRepository;
        this.iykescodeSchoolProps = iykescodeSchoolProps;
        System.out.println("Contact service has been initialized");
    }

    /**
     * Save Contact Details into DB
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;

        contact.setStatus(IykescodeSchoolConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if(savedContact.getContactId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = iykescodeSchoolProps.getPageSize();

        if(null!=iykescodeSchoolProps.getContact() && null!=iykescodeSchoolProps.getContact().get("pageSize")) {
            pageSize = Integer.parseInt(iykescodeSchoolProps.getContact().get("pageSize").trim());
        }

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatus(
                IykescodeSchoolConstants.OPEN,pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;

        int rows = contactRepository.updateStatusById(IykescodeSchoolConstants.CLOSE, contactId);
        if(rows > 0) {
            isUpdated = true;
        }

        return isUpdated;
    }
}
