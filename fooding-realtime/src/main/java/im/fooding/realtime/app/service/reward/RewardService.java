package im.fooding.realtime.app.service.reward;


import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.realtime.app.domain.reward.RewardPoint;
import im.fooding.realtime.app.repository.reward.RewardPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {
    private final RewardPointRepository repository;

    public Mono<RewardPoint> get(long id ){
        return repository.findById( id ).filter( it -> !it.isDeleted() )
                .switchIfEmpty(Mono.error(new ApiException(ErrorCode.REWARD_NOT_FOUND)));
    }

    public Mono<Page<RewardPoint>> list(String searchString, Long storeId, String phoneNumber, Pageable pageable){
        return repository.list( searchString, pageable, storeId, phoneNumber );
    }

    public Mono<RewardPoint> create( Long storeId, String phoneNumber, int point, Long userId ){
        RewardPoint reward = RewardPoint.builder()
                .storeId( storeId )
                .phoneNumber( phoneNumber )
                .point( point )
                .userId( userId != 0 ? userId : null )
                .build();
        return repository.save( reward );
    }

    public Mono<RewardPoint> addPoint( String phoneNumber, Long storeId, int point ){
        Mono<RewardPoint> rewardPointMono = repository.findByPhoneNumberAndStoreIdAndDeletedIsFalse( phoneNumber, storeId ).switchIfEmpty(
                Mono.error( new ApiException(ErrorCode.REWARD_NOT_FOUND))
        );
        return rewardPointMono.flatMap( fl -> {
            fl.addPoint( point );
            return repository.save( fl );
        });
    }

    public Mono<Void> usePoint( String phoneNumber, Long storeId, int point ){
        return repository.findByPhoneNumberAndStoreIdAndDeletedIsFalse( phoneNumber, storeId ).switchIfEmpty(Mono.error(new ApiException(ErrorCode.REWARD_NOT_FOUND)))
                .flatMap( rewardPoint -> {
                    rewardPoint.usePoint( point );
                    return repository.save( rewardPoint );
                }).then();
    }

    public Mono<Void> delete( Long id, Long deletedBy ){
        return repository.findById( id ).switchIfEmpty(Mono.error(new ApiException(ErrorCode.REWARD_NOT_FOUND)))
                .flatMap( rewardpoint -> {
                    rewardpoint.delete( deletedBy );
                    return repository.save( rewardpoint );
                } ).then();
    }

    public Mono<RewardPoint> findByUserIdAndStoreId( long userId, long storeId ){
        return repository.findByUserIdAndStoreIdAndDeletedIsFalse( userId, storeId );
    }

    public Mono<RewardPoint> findByStoreIdAndPhoneNumber( long storeId, String phoneNumber ){
        return repository.findByPhoneNumberAndStoreIdAndDeletedIsFalse( phoneNumber, storeId );
    }

}

