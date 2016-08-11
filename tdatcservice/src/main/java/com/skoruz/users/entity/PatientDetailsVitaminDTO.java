	package com.skoruz.users.entity;

	import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonManagedReference;

	@Entity
	@Table(name="patient_details")
	
	public class PatientDetailsVitaminDTO   implements  Serializable {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		



		public PatientDetailsVitaminDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		
		
		public PatientDetailsVitaminDTO(int patient_id, User user,
				boolean notificationAlert, boolean isPrimarySubscriber,
				boolean assesmentCompletionAlert,
				boolean assesmentNotificationAlert, Integer totalAmountPaid,
				int healthPlanId, PatientVitaminDetails vitaminDetails) {
			super();
			this.patient_id = patient_id;
			this.user = user;
			this.notificationAlert = notificationAlert;
			this.isPrimarySubscriber = isPrimarySubscriber;
			this.assesmentCompletionAlert = assesmentCompletionAlert;
			this.assesmentNotificationAlert = assesmentNotificationAlert;
			this.totalAmountPaid = totalAmountPaid;
			this.healthPlanId = healthPlanId;
			this.vitaminDetails = vitaminDetails;
		}




		public static long getSerialversionuid() {
			return serialVersionUID;
		}



		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="patient_id")
		private int patient_id;

		

		@OneToOne(fetch=FetchType.EAGER)
		@JoinColumn(name="user_id")
		private User user;
		
		
		public int getPatient_id() {
			return patient_id;
		}
		public void setPatient_id(int patient_id) {
			this.patient_id = patient_id;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		@Column(name="notification_alert")
		private boolean notificationAlert;
		
		@Column(name="is_primary_subscriber")
		private boolean isPrimarySubscriber;
		
		@Column(name="assessment_completion_alert")
		private boolean assesmentCompletionAlert;
		
		@Column(name="assessment_notification_alert")
		private boolean assesmentNotificationAlert;
		
		@Column(name="total_amount_paid")
		private Integer totalAmountPaid;
		
		@Column(name="health_plan_id")
		private int healthPlanId;
		
		@OneToOne(fetch = FetchType.EAGER, mappedBy = "patientDetailsVitaminDTO", cascade = CascadeType.ALL)
		@JsonManagedReference
		private PatientVitaminDetails vitaminDetails;
		

		public boolean isNotificationAlert() {
			return notificationAlert;
		}
		public void setNotificationAlert(boolean notificationAlert) {
			this.notificationAlert = notificationAlert;
		}
		public boolean getIsPrimarySubscriber() {
			return isPrimarySubscriber;
		}
		public void setPrimarySubscriber(boolean isPrimarySubscriber) {
			this.isPrimarySubscriber = isPrimarySubscriber;
		}
		public boolean isAssesmentCompletionAlert() {
			return assesmentCompletionAlert;
		}
		public void setAssesmentCompletionAlert(boolean assesmentCompletionAlert) {
			this.assesmentCompletionAlert = assesmentCompletionAlert;
		}
		public boolean isAssesmentNotificationAlert() {
			return assesmentNotificationAlert;
		}
		public void setAssesmentNotificationAlert(boolean assesmentNotificationAlert) {
			this.assesmentNotificationAlert = assesmentNotificationAlert;
		}
		public Integer getTotalAmountPaid() {
			return totalAmountPaid;
		}
		public void setTotalAmountPaid(Integer totalAmountPaid) {
			this.totalAmountPaid = totalAmountPaid;
		}
		public int getHealthPlanId() {
			return healthPlanId;
		}
		public void setHealthPlanId(int healthPlanId) {
			this.healthPlanId = healthPlanId;
		}
		
		public PatientVitaminDetails getVitaminDetails() {
			return vitaminDetails;
		}
		public void setVitaminDetails(PatientVitaminDetails vitaminDetails) {
			this.vitaminDetails = vitaminDetails;
		}
		
		

	}






