package org.thuanthm.lambdaRDSsample1.controller;

//region IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thuanthm.lambdaRDSsample1.dto.User;
import org.thuanthm.lambdaRDSsample1.service.UserService;
//endregion

@RestController
@RequestMapping("/user")
public class UserController {

    //region INIT
    @Autowired
    UserService accountService;
    //endregion

    @GetMapping("/{id}")
    public User one(@PathVariable int id) {
        return accountService.findUser(id);
    }

    @PostMapping("/")
    public boolean add(@RequestBody User user) {
        accountService.insertUser(user);
        return true;
    }
}
