# FetchRewardsExercise
Exercise for Fetch Rewards interview process.

This app uses a MVVM architecture with an added layer of Use Cases.

The app is structured as data, di, domain, presentation.

  data -> recycler view adapter, retrofit api, and repository implementation.
  
  di -> includes all dagger hilt modules.
  
  domain -> data models, repository interface, and use cases(Logic that uses repository and does all data computations).
  
  presentation -> MainActivity, Fragment and it's view model.
  
For testing, I create a fake repository to avoid calling the real api.

For unit tests, I test the only use case with this fake repository.

For instrumented tests, I use dagger hilt to inject the fake repository and use case into my view model and then do some simple fragment tests.
  
