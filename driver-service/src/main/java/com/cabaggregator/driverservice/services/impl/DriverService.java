package com.cabaggregator.driverservice.services.impl;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.core.dto.DriverAddingDTO;
import com.cabaggregator.driverservice.core.dto.DriverDTO;
import com.cabaggregator.driverservice.core.mappers.DriverMapper;
import com.cabaggregator.driverservice.dao.repository.DriverRepository;
import com.cabaggregator.driverservice.entities.Driver;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import com.cabaggregator.driverservice.services.IDriverService;
import com.cabaggregator.driverservice.validator.DriverValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService implements IDriverService {

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    private final DriverValidator driverValidator;

    @Autowired
    public DriverService(DriverRepository driverRepository, DriverMapper driverMapper, DriverValidator driverValidator) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
        this.driverValidator = driverValidator;
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(driverMapper::entityToDto)
                .toList();
    }

    @Override
    public DriverDTO getDriverById(long id) {
        return driverMapper.entityToDto(
                driverRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_DRIVER_WITH_ID_NOT_FOUND,
                                id)));
    }

    @Override
    public DriverDTO getDriverByPhone(String phone) {
        return driverMapper.entityToDto(
                driverRepository.findByPhoneNumber(phone).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_DRIVER_WITH_PHONE_NOT_FOUND,
                                phone)));
    }

    @Override
    public DriverDTO getDriverByEmail(String email) {
        return driverMapper.entityToDto(
                driverRepository.findByEmail(email).orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageKeys.ERROR_DRIVER_WITH_EMAIL_NOT_FOUND,
                                email)));
    }

    @Override
    @Transactional
    public DriverDTO saveDriver(DriverAddingDTO driverDTO) {
        driverValidator.checkPhoneUniqueness(driverDTO.getPhoneNumber());
        driverValidator.checkEmailUniqueness(driverDTO.getEmail());
        driverValidator.checkExistenceOfDriverCar(driverDTO.getCarId());

        return driverMapper.entityToDto(
                driverRepository.save(
                        driverMapper.addingDtoToEntity(driverDTO)));
    }

    @Override
    @Transactional
    public DriverDTO updateDriver(DriverDTO driverDTO) {
        Driver driverToUpdate = driverRepository.findById(driverDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        MessageKeys.ERROR_DRIVER_WITH_ID_NOT_FOUND,
                        driverDTO.getId()));

        driverValidator.checkExistenceOfOtherDriverWithSamePhone(driverToUpdate);
        driverValidator.checkExistenceOfOtherDriverWithSameEmail(driverToUpdate);

        driverToUpdate = driverMapper.dtoToEntity(driverDTO);

        return driverMapper.entityToDto(driverRepository.save(driverToUpdate));
    }

    @Override
    @Transactional
    public void deleteDriverById(long id) {
        driverValidator.checkExistenceOfDriverWithId(id);
        driverRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllDrivers() {
        if (driverRepository.count() == 0) {
            throw new ResourceNotFoundException(MessageKeys.ERROR_DRIVERS_NOT_FOUND);
        } else {
            driverRepository.deleteAll();
        }
    }
}
