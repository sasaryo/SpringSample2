/**
 *
 */
package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.domain.service.RestService;

/**
 * @author 佐々木亮
 *
 */
@RestController
public class UserRestController {

	@Autowired
	RestService service;
}
