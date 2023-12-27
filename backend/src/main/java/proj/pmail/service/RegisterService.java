package proj.pmail.service;

import proj.pmail.entity.User;

public interface RegisterService {
    Integer register(User user) throws Exception;
}
