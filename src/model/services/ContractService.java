package model.services;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService paymentService;

	public ContractService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public OnlinePaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(OnlinePaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void processContract(Contract contract, Integer months) {

		double monthInterest = contract.getTotalValue() / months;
		double amount;
		for (int i = 1; i <= months; i++) {
			
			amount = monthInterest + paymentService.interest(monthInterest, i);
			amount += paymentService.paymentFee(amount);
			
			Installment installment = new Installment
					(contract.getDate().plusMonths(i), amount);
			
			contract.getInstallments().add(installment);
		}
	}
}
