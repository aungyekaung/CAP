package edu.iss.caps.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.iss.caps.model.Courseinfo;
import edu.iss.caps.model.Department;
import edu.iss.caps.model.Faculty;
import edu.iss.caps.model.Loginrole;
import edu.iss.caps.model.Student;
import edu.iss.caps.model.Studentgrade;
import edu.iss.caps.model.User;
import edu.iss.caps.service.CourseinfoService;
import edu.iss.caps.service.DepartmentService;
import edu.iss.caps.service.FacultyService;
import edu.iss.caps.service.LoginroleService;
import edu.iss.caps.service.StudentService;
import edu.iss.caps.service.StudentgradeService;
import edu.iss.caps.service.UserService;
import edu.iss.caps.validator.CourseinfoValidator;
//import edu.iss.caps.model.Student;
import edu.iss.caps.validator.DepartmentValidator;
import edu.iss.caps.validator.FacultyValidator;

@Controller
@RequestMapping(value = "/AdminEnrol")
public class AdminEnrolmentController {

	@Autowired
	private UserService uservice;

	@Autowired
	private StudentService sservice;

	@Autowired
	private LoginroleService lrservice;

	@Autowired
	private DepartmentService dservice;

	@Autowired
	private FacultyService fservice;
	
	@Autowired
	private DepartmentValidator dValidator;
	
	@Autowired
	private FacultyValidator fValidator;
	
	@Autowired
	private CourseinfoService courseService;
	
	@Autowired
	private CourseinfoValidator courseValidator;
	
	@Autowired
	private StudentgradeService gradeService;
	
	@InitBinder("department")
	private void initDepartmentBinder(WebDataBinder binder) {
		binder.addValidators(dValidator);
	}
	
	@InitBinder("courseInfo")
	private void initCourseinfoBinder(WebDataBinder binder){
		binder.addValidators(courseValidator);
	}
	
	@InitBinder("faculty")
	private void initFacultyBinder(WebDataBinder binder) {
		binder.addValidators(fValidator);
	}
	
	@RequestMapping(value = "/manageEnrolment", method = RequestMethod.GET)
	public ModelAndView studListManage( Model model, HttpSession session, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("adminPage/adminRole/enrolManage/selectCourse");
		mav.addObject("clist", courseService.findAllActiveCourses());
		return mav;
	}

	

	@RequestMapping(value = "/selectStudent", method = RequestMethod.POST)
	public ModelAndView selectStudent(Model model, HttpSession session, HttpServletRequest request) {
		int courseId = Integer.parseInt(request.getParameter("course-select"));
		ModelAndView mav = new ModelAndView("adminPage/adminRole/enrolManage/enroleStudent");
		ArrayList<Studentgrade> enrolStstus = gradeService.getStudentGradeByCourse(courseId);
		ArrayList<Student> students = sservice.findAllStudents();
		mav.addObject("enrolStstus", enrolStstus);
		mav.addObject("students", students);
		mav.addObject("courseId", courseId);
		return mav;
	}

	@RequestMapping(value = "/student-enrole-admin/{studentId}/{courseId}", method = RequestMethod.POST)
	public ModelAndView studentEnroleAdmin(Model model, HttpSession session, @PathVariable String studentId, @PathVariable String courseId) {
		Student stu = sservice.findOneStudent(Integer.parseInt(studentId));
		Courseinfo crs = courseService.findCourseinfo(Integer.parseInt(courseId));
		Studentgrade enrolment = new Studentgrade();
		ArrayList<Studentgrade> grades = gradeService.viewEnrolmentByCourseID(Integer.parseInt(courseId));
		enrolment.setCompletionStatus("Enroled");
		enrolment.setCourseID(crs);
		enrolment.setEnrolledDate(new Date());
		enrolment.setStudentID(stu);
		if(crs.getMaxClassSize() < grades.size()){
			return null;
		}else{
			Studentgrade newEnr = gradeService.createEnrolment(enrolment);
			return new ModelAndView("adminPage/adminRole/enrolManage/enroleStudent");
		}
	}

}
