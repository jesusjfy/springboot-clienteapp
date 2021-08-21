package com.springboot.clienteapp.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes attribute) {

		if (error != null) {
			model.addAttribute("error", "ERROR DE ACCESO: Usuario y/o contraseña incorrectas");
		}

		if (principal != null) {
			attribute.addFlashAttribute("warning", "ATENCIÓN: Ud ya ha iniciado sesión previamente");
			return "redirect:/index";
		}

		if (logout != null) {
			model.addAttribute("success", "ATENCIÓN: Ha finalizado sesión con éxito");
		}

		return "login";
	}

}
