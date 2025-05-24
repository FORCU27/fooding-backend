package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminSearchStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePosition;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.model.user.UserAuthority;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public PageResponse<AdminStoreResponse> list(AdminSearchStoreRequest request) {
        Page<Store> result = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), false);
        PageInfo pageInfo = PageInfo.of(result);
        return PageResponse.of(
                result.getContent().stream().map(AdminStoreResponse::new).collect(Collectors.toList()),
                pageInfo);
    }

    @Transactional(readOnly = true)
    public AdminStoreResponse retrieve(Long id) {
        Store store = storeService.findById(id);
        return new AdminStoreResponse(store);
    }

    @Transactional
    public Long create(AdminCreateStoreRequest request) {
        User user = userService.findById(request.getOwnerId());
        List<Role> roles = user.getAuthorities().stream().map(UserAuthority::getRole).toList();
        if (!roles.contains(Role.CEO)) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }

        Store store = storeService.create(user, request.getName(), request.getCity(), request.getAddress(), request.getCategory(),
                request.getDescription(), request.getPriceCategory(), request.getEventDescription(), request.getContactNumber(),
                request.getDirection(), request.getInformation(), request.getIsParkingAvailable(), request.getIsNewOpen(),
                request.getIsTakeOut());

        storeMemberService.create(store, user, StorePosition.OWNER);

        return store.getId();
    }

    @Transactional
    public void update(Long id, AdminUpdateStoreRequest request) {
        storeService.update(id, request.getName(), request.getCity(), request.getAddress(), request.getCategory(), request.getDescription(),
                request.getContactNumber(), request.getPriceCategory(), request.getEventDescription(), request.getDirection(),
                request.getInformation(), request.getIsParkingAvailable(), request.getIsNewOpen(), request.getIsTakeOut());
    }

    @Transactional
    public void delete(Long id) {
        storeService.delete(id, null);
    }
}
