pragma solidity >=0.7.0 <0.9.0;

import "./Ownable.sol";

library MyType {
    struct Data {
        string title;
        string description;
        uint8 voteType;
        string imagePath;
        bool bImageExist;
        bool bShowDetail;
        uint256 createdAt;
        uint256 endedAt;
        Item[] items;
    }

    struct Item {
        string title;
        string description;
        string imagePath;
        uint256 count;
    }
}

contract VoteManager {
    mapping(uint256 => Vote) votes;
    uint256[] voteIds;

    event VoteCreated(uint256);

    constructor() {}

    modifier sizeAvailable(uint256 size) {
        require(size < voteIds.length);
        _;
    }

    function setVote(MyType.Data memory _data) public {
        uint256 idx = voteIds.length;
        voteIds.push(idx);
        votes[idx] = new Vote(_data);

        emit VoteCreated(idx);
    }

    function getVote(uint256 idx)
        public
        view
        sizeAvailable(idx)
        returns (MyType.Data memory)
    {
        return votes[idx].getVote();
    }

    function voteTo(uint256 idx, uint256 itemId) public sizeAvailable(idx) {
        votes[idx].voteTo(itemId, msg.sender);
    }

    function isVoteEnd(uint256 idx)
        public
        view
        sizeAvailable(idx)
        returns (bool)
    {
        return votes[idx].isVoteEnd();
    }

    function isVote(uint256 idx) public view sizeAvailable(idx) returns (bool) {
        return votes[idx].isVote(msg.sender);
    }
}

contract Vote is Ownable {
    MyType.Data data;

    mapping(address => bool) addressIsVote;

    event VoteSuccess(MyType.Data);

    constructor(MyType.Data memory _data) public {
        data.title = _data.title;
        data.description = _data.description;
        data.voteType = _data.voteType;
        data.imagePath = _data.imagePath;
        data.bImageExist = _data.bImageExist;
        data.bShowDetail = _data.bShowDetail;
        data.createdAt = block.timestamp;
        data.endedAt = _data.endedAt / 1000;
        for (uint256 i = 0; i < _data.items.length; i++) {
            data.items.push(_data.items[i]);
            data.items[i].count = 0;
        }
    }

    function voteTo(uint256 itemId, address _from) public {
        require(itemId < data.items.length, "Index not available");
        require(block.timestamp < data.endedAt, "Vote ended");
        require(addressIsVote[_from] != true, "Already voted");
        addressIsVote[_from] = true;

        data.items[itemId].count++;

        emit VoteSuccess(data);
    }

    function isVoteEnd() public view returns (bool) {
        if (block.timestamp > data.endedAt) return true;
        return false;
    }

    function isVote(address _from) public view returns (bool) {
        if (addressIsVote[_from] != true) return false;
        return true;
    }

    function getVote() public view returns (MyType.Data memory) {
        return data;
    }
}
