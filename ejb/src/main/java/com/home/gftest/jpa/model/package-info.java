/**
 * The sample entity classes.<br>
 * <p>
 * <strong>Order - OrderItem</strong><br>
 * <p>
 * OneToMany - ManyToOne<br>
 * FetchType.LAZY for better performance<br>
 * CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE for referential integrity<br>
 * <p>
 * <strong>Delivery - Component</strong><br>
 * <p>
 * ManyToMany<br>
 * A delivery contains 1:N components - A component is part of 0:N deliveries<br>
 * <p>
 * <strong>User - Address</strong><br>
 * <p>
 * OneToOne - OneToOne (Bidirectional)<br>
 * Mandatory relationship: User has always an Address but an Address can be without User
 * FetchType.LAZY for better performance<br>
 * CascadeType.ALL for referential integrity<br>
 */
package com.home.gftest.jpa.model;
