const Google = {
    async GoogleLoginSuccess(googleUser) {
        const req_body = {
            googleAccessToken: googleUser.Zi.access_token,
            name: googleUser.w3.ig,
            id: googleUser.w3.Eea,
            email: googleUser.w3.U3,
            profileIMG: googleUser.w3.paa,
            source: 'g',
        };
        await social_login(req_body);

    },

    GoogleLoginFailure() {
        this.GoogleLoginFailure();
        console.log('fail');
    },

    Signout() {
        const auth2 = gapi.auth2.getAuthInstance();
        console.log(auth2);
        auth2
            .signOut()
            .then(async () => {
                await social_logout();
            })
            .catch(() => {
                LogoutFailure();
            });
    }
}