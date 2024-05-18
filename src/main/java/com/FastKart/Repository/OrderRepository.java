package com.FastKart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.FastKart.entities.Order;
import com.FastKart.entities.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	
	int countByUser(User user);
	
	 // Method to count delivered orders for a specific user
    int countByStatusAndUser(int status, User user);
    
    List<Order> getOrdersByUser(User user);
    
	/*
	 * @Query("SELECT o.product, SUM(o.quantity) AS totalSold " + "FROM Order o " +
	 * "GROUP BY o.product " + "ORDER BY totalSold DESC") List<Object[]>
	 * findTopSellingProducts();
	 */
    
    
    
    @Query("SELECT o.product, SUM(o.quantity) AS totalSold " +
            "FROM Order o " +
            "GROUP BY o.product " +
            "ORDER BY totalSold DESC " +
            "LIMIT 4")
    List<Object[]> findTopSellingProducts();
    
    

    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC LIMIT 4")
    List<Order> findLatestOrders();
    
    // count all Order
    int countAllBy();
    
    /*  Or 
       @Query("SELECT COUNT(o) FROM Order o" )
       int countAllOrder();
     */

    // to count total customer (user) Distict give use unique value 
    @Query("SELECT COUNT(DISTINCT o.user.id) FROM Order o")
    int countDistinctUsers();
}
