// package com.capstone.licencelifecyclemanagement.controllers;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;

// import com.capstone.licencelifecyclemanagement.entitys.Plan;
// import com.capstone.licencelifecyclemanagement.services.PlanService;

// @RestController
// @RequestMapping("/plan")
// public class PlanController {

//     @Autowired
//     private PlanService planservice;

//     @PostMapping("/add")
//     public Plan addPlan (@RequestBody Plan plan)
//     {
//         return planservice.addPlan(plan);
//     }

//     @GetMapping("/getPlans")
//     public List<Plan> getPlans()
//     {
//         return planservice.getPlans();
//     }
    
// }
