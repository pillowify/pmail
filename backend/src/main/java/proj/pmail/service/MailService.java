package proj.pmail.service;

import proj.pmail.dto.MailDTO;
import proj.pmail.entity.Envelope;
import proj.pmail.entity.Mail;
import proj.pmail.vo.DraftVO;

import java.io.InputStream;
import java.util.List;

public interface MailService {

    String formatFolderName(String folder);

    List<Envelope> getEnvelopeList(String username, String folder) throws Exception;

    List<Envelope> getFullEnvelopeList(String username, String folder) throws Exception;

    boolean markMailAsRead(String username, String folder, Integer[] ids) throws Exception;

    boolean markMailAsUnread(String username, String folder, Integer[] ids) throws Exception;

    boolean deleteMail(String username, String folder, Integer[] ids, Boolean permanent) throws Exception;

    boolean moveMail(String username, String folder, Integer[] ids, String destination) throws Exception;

    Mail getMail(String username, String folder, Long id) throws Exception;

    boolean sendMail(MailDTO mailDTO);

    boolean saveMailToDrafts(MailDTO mailDTO) throws Exception;

    DraftVO getDraftVO(String username, String folder, Long id) throws Exception;

    InputStream getAttachmentInputStream(String username, String folder, Long id, String fileName) throws Exception;
}
