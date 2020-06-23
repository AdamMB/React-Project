const initialState= {status:'GOOD'}

function updateStatus(state = initialState, action) {
    let nextState;
    switch (action.type) {
        case 'GOOD':
            console.log("state : " + action.value);
            nextState = { ...state, status:action.value};
            return nextState || state;
        case 'BADY':
            console.log('BAD YAWS');
            nextState = { ...state, status:"BADY"};
            return nextState || state;
        case 'TF':
            console.log('TOO FAR');
            nextState = { ...state, status:"TF"};
            return nextState || state;
        default:
            return state
    }
}

export default updateStatus
