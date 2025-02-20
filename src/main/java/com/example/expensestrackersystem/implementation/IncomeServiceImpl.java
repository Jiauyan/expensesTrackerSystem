package com.example.expensestrackersystem.implementation;

import com.example.expensestrackersystem.dto.IncomeDTO;
import com.example.expensestrackersystem.entity.Income;
import com.example.expensestrackersystem.repository.IncomeRepository;
import com.example.expensestrackersystem.service.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
 public class IncomeServiceImpl implements IncomeService {

      private final IncomeRepository incomeRepository;

      public Income postIncome(IncomeDTO incomeDTO) {
       return saveOrUpdateIncome(new Income(), incomeDTO);
      }

      private Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO) {
       income.setTitle(incomeDTO.getTitle());
       income.setDate(incomeDTO.getDate());
       income.setAmount(incomeDTO.getAmount());
       income.setCategory(incomeDTO.getCategory());
       income.setDescription(incomeDTO.getDescription());
       income.setUserId(incomeDTO.getUserId());

       return incomeRepository.save(income);
      }

      public List<Income> getAllIncomes() {
       return incomeRepository.findAll().stream()
               .sorted(Comparator.comparing(Income::getDate).reversed())
               .collect(Collectors.toList());
      }

      public List<Income> getAllIncomesByUserId(Integer userId) {
       return incomeRepository.findAllByUserId(userId).stream()
               .sorted(Comparator.comparing(Income::getDate).reversed())
               .collect(Collectors.toList());
      }

      public Income getIncomeById(Integer id){
       Optional<Income> optionalIncome = incomeRepository.findById(id);
       if(optionalIncome.isPresent()){
        return optionalIncome.get();
       } else {
        throw new EntityNotFoundException("Income is not present with id" + id);
       }
      }

      public Income updateIncome(Integer id, IncomeDTO incomeDTO){
       Optional<Income> optionalIncome = incomeRepository.findById(id);
       if(optionalIncome.isPresent()){
        return saveOrUpdateIncome(optionalIncome.get(), incomeDTO);
       } else {
        throw new EntityNotFoundException("Income is not present with id" + id);
       }
      }

      public void deleteIncome(Integer id){
       Optional<Income> optionalIncome = incomeRepository.findById(id);
       if(optionalIncome.isPresent()){
        incomeRepository.deleteById(id);
       } else {
        throw new EntityNotFoundException("Income is not present with id" + id);
       }
      }

    public List<Income> searchIncomeByTitle(String title, Integer userId) {
        List<Income> incomes = incomeRepository.findByTitleContainingIgnoreCaseAndUserId(title, userId);
        if (incomes.isEmpty()) {
            throw new EntityNotFoundException("No incomes found with title containing: " + title + " for user ID: " + userId);
        }
        return incomes;
    }

}
