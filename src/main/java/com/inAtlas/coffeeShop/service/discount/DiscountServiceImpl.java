package com.inAtlas.coffeeShop.service.discount;

import com.inAtlas.coffeeShop.repository.discount.DiscountRepository;
import com.inAtlas.coffeeShop.repository.entity.DiscountEntity;
import com.inAtlas.coffeeShop.repository.entity.DiscountItemEntity;
import com.inAtlas.coffeeShop.repository.product.ProductRepository;
import com.inAtlas.coffeeShop.service.dto.DiscountDto;
import com.inAtlas.coffeeShop.utils.Constants;
import com.inAtlas.coffeeShop.utils.functions.EntityToDtoAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public DiscountDto add(DiscountDto obj) {
        DiscountEntity discountEntity = new DiscountEntity();
        return getDiscountDto(obj, discountEntity);
    }

    @Override
    @Transactional
    public DiscountDto update(Long id, DiscountDto discountDto) {
        DiscountEntity discountEntity = discountRepository.getById(id);
        return getDiscountDto(discountDto, discountEntity);
    }

    private DiscountDto getDiscountDto(DiscountDto obj, DiscountEntity discountEntity) {
        discountEntity.setStatus(obj.getStatus());
        discountEntity.setFromDate(obj.getFromDate());
        discountEntity.setToDate(obj.getToDate());
        discountEntity.setDiscount(obj.getDiscount());
        for (int i = 0; i < obj.getDiscountItems().size(); i++) {
            DiscountItemEntity discountItem = new DiscountItemEntity();
            discountItem.setProduct(productRepository.getById(obj.getDiscountItems().get(i).getProduct().getId()));
            discountEntity.addDiscountItem(discountItem);
        }
        return EntityToDtoAdapter.discountEntityToDiscountDtoAdapter.apply(discountRepository.saveAndFlush(discountEntity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DiscountEntity DiscountEntity = discountRepository.getById(id);
        DiscountEntity.setStatus("INATIVO");
        discountRepository.save(DiscountEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDto> getAll() {
        return discountRepository.findAll()
                .stream()
                .map(EntityToDtoAdapter.discountEntityToDiscountDtoAdapter)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountDto getById(Long id) {
        return EntityToDtoAdapter.discountEntityToDiscountDtoAdapter
                .apply(discountRepository.findById(id).orElseThrow(() ->
                        new EntityNotFoundException(Constants.DISCOUNT_NOT_FOUND)));
    }



}
