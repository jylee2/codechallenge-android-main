import com.sonder.domain.models.SearchSectionResult
import com.sonder.domain.repositories.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(query: String): Flow<Result<List<SearchSectionResult>>> =
        repository.search(query)
}