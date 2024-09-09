package EmployeePackage;


import EmployeePackage.Controllers.EmployeeController;
import EmployeePackage.Entities.AddressEntity;
import EmployeePackage.Entities.DepartmentEntity;
import EmployeePackage.Entities.EmployeeEntity;
import EmployeePackage.Extras.APIResponse;
import EmployeePackage.Repositories.EmployeeRepo;
import EmployeePackage.Services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apiguardian.api.API;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class EmployeeApplicationTests {

	@Autowired ObjectMapper objectMapper;
	MockMvc mockMvc;

	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeService employeeService;
//
//	@Mock
//	EmployeeRepo employeeRepo;


	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext){

		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void postEmployee_valid_returnsCreated() throws Exception {

		EmployeeEntity employee = new EmployeeEntity(200,"employee1",30,new AddressEntity("address1","city1",1234),new DepartmentEntity(101));

		when(employeeController.postEmployee(employee)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new APIResponse<>(200,"Employee Created Successfully",employee)));
		mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(jsonPath("$.message").value("Employee Created Successfully"));
	}

//	@Test
//	void postEmployee_Existing_returnsBAD_REQUEST() throws Exception {
//		EmployeeEntity employee = new EmployeeEntity(101,"employee1",30);
//		when(employeeService.createEmployeeService(any(EmployeeEntity.class))).thenReturn(new APIResponse<>(200.1,"Employee Already Exists",null));
//		mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(jsonPath("$.message").value("Employee Already Exists"));
//	}
//
//	@Test
//	void postEmployee_Partial_NOID_returnsBAD_REQUEST() throws Exception {
//		EmployeeEntity employee = new EmployeeEntity("employee1",30);
//		when(employeeService.createEmployeeService(any(EmployeeEntity.class))).thenReturn(new APIResponse<>(200.1,"Full Details Not Provided",null));
//		mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(jsonPath("$.message").value("Full Details Not Provided"));
//	}
//
//	@Test
//	void postEmployee_Partial_NONAME_returnsBAD_REQUEST() throws Exception {
//		EmployeeEntity employee = new EmployeeEntity(300,30);
//		when(employeeService.createEmployeeService(any(EmployeeEntity.class))).thenReturn(new APIResponse<>(200.1,"Full Details Not Provided",null));
//		mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(jsonPath("$.message").value("Full Details Not Provided"));
//	}
//
//	@Test
//	void postEmployee_Partial_NOAGE_returnsBAD_REQUEST() throws Exception {
//		EmployeeEntity employee = new EmployeeEntity(300,"employee1");
//		when(employeeService.createEmployeeService(any(EmployeeEntity.class))).thenReturn(new APIResponse<>(200.1,"Full Details Not Provided",null));
//		mockMvc.perform(MockMvcRequestBuilders.post("/employee").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(MockMvcResultMatchers.status().isBadRequest()).andExpect(jsonPath("$.message").value("Full Details Not Provided"));
//	}

}
