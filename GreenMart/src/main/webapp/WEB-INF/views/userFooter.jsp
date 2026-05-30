<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="greenmart-html" lang="en">

<div class="greenmart-body">

<div class="greenmart-content-wrapper">
  <div class="greenmart-main-content">
  </div>

  <footer class="greenmart-footer shadow-lg bg-light text-dark">
    <div class="container py-4">
      <div class="row text-center text-md-left">
        <!-- About GreenMart -->
        <div class="col-md-4 mb-4">
          <h5 class="text-success font-weight-bold">About GreenMart</h5>
          <p>Your one-stop destination for high-quality plants, gardening tools, and eco-friendly products. We aim to bring nature closer to you with a hassle-free shopping experience.</p>
        </div>

        <!-- Customer Service -->
        <div class="col-md-4 mb-4">
          <h5 class="text-success font-weight-bold">Customer Service</h5>
         <ul class="list-unstyled">
 			 <li><a href="${pageContext.request.contextPath}/" class="text-muted">🏡 Home</a></li>
 			 <li><a href="${pageContext.request.contextPath}/user/orders" class="text-muted">🛒 Track Order</a></li>
 			 <li><a href="#" class="text-muted show-content" data-target="returns-policy">🔄 Return & Refund Policy</a></li>
 			 <li><a href="#" class="text-muted show-content" data-target="shipping-info">📦 Shipping Information</a></li>
 			 <li><a href="#" class="text-muted show-content" data-target="help-support">💬 Help & Support</a></li>
		</ul>



        </div>

        <!-- Contact & Business Info -->
        <div class="col-md-4 mb-4">
          <h5 class="text-success font-weight-bold">Get in Touch</h5>
          <p>📍 <strong>Our Location:</strong> 123 Green Street, Garden City, Earth 🌍</p>
          <p>📧 <strong>Email:</strong> <a href="mailto:greenmartteam@gmail.com" class="text-muted">greenmartteam@gmail.com</a></p>
          <p>📞 <strong>Call Us:</strong> <a href="tel:+1234567890" class="text-muted">+1 234 567 890</a></p>
          <p>🕒 <strong>Working Hours:</strong> Mon - Sat, 9:00 AM - 6:00 PM</p>
        </div>
      </div>

      <!-- Social Media Links -->
      <div class="text-center mt-4">
        <h5 class="text-success font-weight-bold">Follow Us</h5>
        <a href="#" class="mx-2"><img src="https://img.icons8.com/ios-filled/30/4CAF50/facebook-new.png" alt="Facebook"></a>
        <a href="#" class="mx-2"><img src="https://img.icons8.com/ios-filled/30/4CAF50/twitter-circled.png" alt="Twitter"></a>
        <a href="#" class="mx-2"><img src="https://img.icons8.com/ios-filled/30/4CAF50/instagram-new.png" alt="Instagram"></a>
        <a href="#" class="mx-2"><img src="https://img.icons8.com/ios-filled/30/4CAF50/linkedin.png" alt="LinkedIn"></a>
      </div>
    </div>
    
<div class="content-section mt-4">
    <div id="returns-policy" class="policy-content d-none">
        <h4>🔄 Return & Refund Policy</h4>
        <p>You can return products within <strong>7 days</strong> of delivery.</p>
        <p>Refunds are processed within <strong>5-7 business days</strong>.</p>
    </div>

    <div id="shipping-info" class="policy-content d-none">
        <h4>📦 Shipping Information</h4>
        <p>We offer fast delivery across multiple locations.</p>
        <p>Estimated delivery: <strong>5-7 business days</strong>.</p>
    </div>

    <div id="help-support" class="policy-content d-none">
        <h4>💬 Help & Support</h4>
        <p>For any issues, contact us at <a href="mailto:greenmartteam@gmail.com">greenmartteam@gmail.com</a></p>
        <p>📞 Call us: <a href="tel:+1234567890">+1 234 567 890</a></p>
    </div>
</div>
    
  </footer>

  <div class="text-center p-3 bg-success text-white">
    &copy; 2024 GreenMart | Bringing Greenery to Every Home 🌱
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $(document).ready(function() {
    $(".show-content").click(function(e) {
        e.preventDefault(); // Prevent page reload

        var target = $(this).data("target"); // Get target div ID
        $(".policy-content").addClass("d-none"); // Hide all sections
        $("#" + target).removeClass("d-none"); // Show selected section
    });
  });
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
</div>
</div>
