package com.company.goodreadsapp.controller;

//
//@RestController
//@RequestMapping("/api/v1/auth")
//@RequiredArgsConstructor
public class AuthController {
//    private final OtpService otpService;
//    private final AuthService authService;
//    private final TokenService tokenService;

//    @PostMapping
//    public ResponseEntity<SignInResponse> signIn(
//            @RequestBody SignInCommand command) {
//        return ResponseEntity.ok(authService.signIn(command));
//    }

//    @PostMapping("/otp/check")
//    public ResponseEntity<Void> checkOtp(
//            @RequestBody CheckOtpCommand command,
//            HttpServletResponse response
//    ) {
//        var otp = otpService.checkOtp(command);
//
//        var token = tokenService.createToken(new CreateTokenCommand(otp.getUserId(), otp.getEmail()));
//
//        response.setHeader(HeaderKeys.ACCESS_TOKEN, token.getAccessToken());
//        response.setHeader(HeaderKeys.REFRESH_TOKEN, token.getRefreshToken());
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                .build();
//    }
}
