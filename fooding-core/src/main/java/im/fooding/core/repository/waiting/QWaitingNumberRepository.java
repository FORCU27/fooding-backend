package im.fooding.core.repository.waiting;

public interface QWaitingNumberRepository {

    int issueNumberByStoreId(long storeId);
}
