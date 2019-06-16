import React, { Component } from "react";
import { translate, Trans } from 'react-i18next';
import Moment from 'react-moment';

class TaskItem extends Component{
    render(){
        return(
            <p>
                {this.props.name} (Deadline: {" "}
                <Moment format="YYYY-MM-DD">
                {this.props.deadline}
            </Moment>)
            </p>
        )
    }
}

export default translate('common')(TaskItem);