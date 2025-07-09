package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.CeoCreateStoreRequest;
import im.fooding.app.dto.request.ceo.store.CeoUpdateStoreRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePosition;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStoreService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<CeoStoreResponse> list(long userId) {
        return storeService.list(userId).stream().map(CeoStoreResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CeoStoreResponse retrieve(Long id, long userId) {
        storeMemberService.checkMember(id, userId);
        return new CeoStoreResponse(storeService.findById(id));
    }

    @Transactional
    public Long create(CeoCreateStoreRequest request, long userId) {
        User user = userService.findById(userId);

        Store store = storeService.create(user, request.getName(), "", "", "",
                "", "", "", "",
                "", "", true, true,
                true, null, null);

        storeMemberService.create(store, user, StorePosition.OWNER);

        return store.getId();
    }

    @Transactional
    public void update(Long id, CeoUpdateStoreRequest request, long userId) {
        storeMemberService.checkMember(id, userId);
        storeService.update(id, request.getName(), request.getCity(), request.getAddress(), request.getCategory(), request.getDescription(),
                request.getContactNumber(), request.getPriceCategory(), request.getEventDescription(), request.getDirection(),
                request.getInformation(), request.getIsParkingAvailable(), request.getIsNewOpen(), request.getIsTakeOut(), request.getLatitude(), request.getLongitude());
    }

    @Transactional
    public void delete(Long id, long deletedBy) {
        storeMemberService.checkMember(id, deletedBy);
        storeService.delete(id, deletedBy);
    }
}
