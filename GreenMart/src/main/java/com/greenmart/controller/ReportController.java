package com.greenmart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greenmart.model.ProductSalesReport;
import com.greenmart.service.ReportService;

@Controller
@RequestMapping("admin/reports/")
public class ReportController {
@Autowired
ReportService reportService;

/*
 * @RequestMapping("/product-sales") public String
 * productSalesReport(HttpSession session,Model model) { String
 * adminId=(String)session.getAttribute("adminName"); if(adminId == null) {
 * 
 * model.addAttribute("errorMessage", "Please login first."); return
 * "adminLogin";
 * 
 * } List<ProductSalesReport>
 * productSalesReport=reportService.getProductSalesReport();
 * model.addAttribute("productSalesReport",productSalesReport); return
 * "productSalesReport"; }
 */
@GetMapping("/product-sales")
public String productSalesReport(@RequestParam(value = "filter", required = false) String filter,
                                 HttpSession session, Model model) {
    String adminId = (String) session.getAttribute("adminName");
    if (adminId == null) {
        model.addAttribute("errorMessage", "Please login first.");
        return "adminLogin";  
    }
    
    List<ProductSalesReport> productSalesReport = reportService.getProductSalesReport(filter);
    
    int totalQuantitySold=0;
    double totalRevenue=0;
    int totalCustomers=0;

   for (ProductSalesReport report : productSalesReport) {
       totalQuantitySold += report.getTotalQuantitySold(); 
       totalRevenue += report.getTotalRevenue(); 
       totalCustomers += report.getTotalUniqueCustomers(); 
       }
   model.addAttribute("productSalesReport", productSalesReport);
   model.addAttribute("selectedFilter", filter);
   model.addAttribute("totalQuantitySold", totalQuantitySold);
   model.addAttribute("totalRevenue", totalRevenue);
   model.addAttribute("totalCustomers", totalCustomers);
     
    return "productSalesReport";
}
	
}
