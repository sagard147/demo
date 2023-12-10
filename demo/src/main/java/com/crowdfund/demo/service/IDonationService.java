package com.crowdfund.demo.service;
import com.crowdfund.demo.mapper.DonationDTO;
import com.crowdfund.demo.model.Donation;

import java.util.List;
public interface IDonationService {
    List<Donation> listAllDonation();

    List<Donation> getUserDonations(Long userId);

    List<Donation> getProjectDonations(Long projectId);

    Donation addDonation(DonationDTO donationCreateDTO);
}
