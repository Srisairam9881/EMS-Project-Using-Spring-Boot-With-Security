package com.example.EMSWithLoginSystem.web.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.EMSWithLoginSystem.model.Employee;
import com.example.EMSWithLoginSystem.repository.EmployeeRepository;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeRepository empRepository;
	@GetMapping({"/list", "/"})
	public ModelAndView getAllEmployees() {
		ModelAndView mav = new ModelAndView("Dashboard");  //here "Dashboard" is a template file name 
		mav.addObject("employees", empRepository.findAll());
		return mav;
	}
	@GetMapping("/addEmployeeForm")
	public ModelAndView addEmployeeForm() {
		ModelAndView mav = new ModelAndView("addEmployee");  //here addEmployee is a template file name 
		Employee newEmployee = new Employee();
		mav.addObject("employee", newEmployee);
		return mav;
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute Employee employee) {
		empRepository.save(employee);
		return "redirect:/list";
	}
	
	@GetMapping("/showUpdateForm")
	public ModelAndView showUpdateForm(@RequestParam Long employeeId) {
		ModelAndView mav = new ModelAndView("addEmployee");
		Employee employee = empRepository.findById(employeeId).get();
		mav.addObject("employee", employee);
		return mav;
	}
	
	@GetMapping("/deleteEmployee")
	public String deleteEmployee(@RequestParam Long employeeId) {
		empRepository.deleteById(employeeId);
		return "redirect:/list";
	}


}
