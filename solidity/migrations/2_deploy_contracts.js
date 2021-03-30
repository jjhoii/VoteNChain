const Vote = artifacts.require("Vote");
const VoteManager = artifacts.require("VoteManager");

module.exports = function (deployer) {
    deployer.deploy(Vote, {
        title: "test",
        description: "test",
        voteType: 0,
        imagePath: "path",
        bImageExist: true,
        bShowDetail: true,
        createdAt: Date.now(),
        endedAt: (new Date("2021-03-30 00:10:00")).getTime(),
        items: [{
            title: "test1",
            data: "test1",
            description: "test1",
            imagePath: "testPath",
            count: 0
        }, {
            title: "test2",
            data: "test2",
            description: "test2",
            imagePath: "testPath2",
            count: 0
        }]
    });
    deployer.deploy(VoteManager);
};