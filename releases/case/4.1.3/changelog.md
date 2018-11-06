# Case 4.1.3 
  
## Bug Fixes
- Fixed a crash that would occur if a Case SDK Activity was restarted by the OS

## Known Issues
- Case SDK Activities that are restarted by the OS may not display any content until the
Case SDK is re-initialized within the app. Consider creating a `CaseUIClient` during `Application.onCreate()`
to avoid this problem.

